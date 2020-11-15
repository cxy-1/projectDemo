package com.sugon.analysis.service;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.TableTools;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xwpf.usermodel.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: �Զ�����������
 * @author: liuwei
 * @create: 2020-04-27 20:45
 **/
public class KPITablePolicy extends AbstractRenderPolicy<List<Map<String,Object >>> {
    @Override
    protected void afterRender(RenderContext<List<Map<String,Object >>> context) {
        // ��ձ�ǩ
        clearPlaceholder(context, true);
    }

    List<Pair<String,String>> heads= Arrays.asList(Pair.of("���˵�λ","SIMPLECHINANAME"),Pair.of("���˳ɼ�","SCORE"),Pair.of("����","RN"));

    @Override
    public void doRender(RenderContext<List<Map<String,Object >>> context) throws Exception {
        List<Map<String, Object>> data = context.getData();
        List<String> groupNames = data.stream().map(p -> String.valueOf(p.get("GROUPNAME"))).distinct().collect(Collectors.toList());
        Map<String, List<Map<String, Object>>> groupByName = data.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("GROUPNAME")), Collectors.toList()));


        XWPFRun run = context.getRun();
        // ��ǰλ�õ�����
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
        // ��ǰλ�ò�����
        XWPFTable table = bodyContainer.insertNewTable(run, 1, 9);
        table.removeRow(0);

        int row=0;
        int lastMaxLength=0;

        for (int i = 0; i < groupNames.size(); i+=3) {
            List<String> groups;
            if(groupNames.size()>=i+3){
                groups = groupNames.subList(i,i+3);
            }else{
                groups = groupNames.subList(i,groupNames.size());
            }
            int maxLength=0;
            if(groups.size()==1){
                int size = groupByName.get(groups.get(0)).size();
                if(size%3==0){
                    maxLength = size/3;
                }else{
                    maxLength = size/3+1;
                }
            }else{
                for (String group : groups) {
                    int size = groupByName.get(group).size();
                    if(maxLength<size){
                        maxLength = size;
                    }
                }
            }

            int index=0;
            //maxLength + 2  ����������ͷ
            for (int m = 0; m < maxLength + 2; m++) {
                XWPFTableRow groupNameRow = table.insertNewTableRow(row);
                for (int n = 0; n < 9; n++) {
                    groupNameRow.createCell();
                }
                if(m==0){//���˵�Ԫ��
                    if(groups.size()==1){
                        TableTools.mergeCellsHorizonal(table, row, 0, 8);

                        XWPFTableCell cell = groupNameRow.getCell(0);
                        cell.setColor("c2c2c2");
                        XWPFParagraph paragraph = cell.getParagraphs().get(0);
                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun xwpfRun = paragraph.createRun();
                        xwpfRun.setText(groups.get(0));
                        xwpfRun.setFontFamily("��������_GBK");
                        xwpfRun.setFontSize(9);
                    }else{
                        TableTools.mergeCellsHorizonal(table, row, 6, 8);
                        TableTools.mergeCellsHorizonal(table, row, 3, 5);
                        TableTools.mergeCellsHorizonal(table, row, 0, 2);
                        for (int s = 0; s < groups.size(); s++) {
                            XWPFTableCell cell = groupNameRow.getCell(s);
                            cell.setColor("c2c2c2");
                            XWPFParagraph paragraph = cell.getParagraphs().get(0);
                            paragraph.setAlignment(ParagraphAlignment.CENTER);
                            XWPFRun xwpfRun = paragraph.createRun();
                            xwpfRun.setFontSize(9);
                            xwpfRun.setFontFamily("��������_GBK");
                            xwpfRun.setText(groups.get(s));
                        }

                    }
                }else if(m==1){//���˵�λ�����˳ɼ���������
                    for (int s = 0; s < 9; s++) {
                        XWPFTableCell cell = groupNameRow.getCell(s);
                        cell.setColor("c2c2c2");
                        Pair<String, String> pair = heads.get(s % 3);
                        XWPFParagraph paragraph = cell.getParagraphs().get(0);
                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun xwpfRun = paragraph.createRun();
                        xwpfRun.setFontSize(9);
                        xwpfRun.setFontFamily("��������_GBK");
                        xwpfRun.setText(pair.getLeft());
                    }
                }else{//����

                    for (int s = 0; s < 9; s++) {
                        XWPFTableCell cell = groupNameRow.getCell(s);
                        XWPFParagraph paragraph = cell.getParagraphs().get(0);
                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun xwpfRun = paragraph.createRun();
                        xwpfRun.setFontSize(9);
                        xwpfRun.setFontFamily("��������_GBK");
                        Pair<String, String> pair = heads.get(s % 3);
                        List<Map<String, Object>> maps;
                        if(groups.size()==1){//��Ԫ����3��1ʱ�����һ����Ԫ
                            maps = groupByName.get(groups.get(0));
                            if(maps.size()>index){
                                xwpfRun.setText(String.valueOf(maps.get(index).get(pair.getRight())));
                            }else{
                                xwpfRun.setText("-");
                            }
                            if(s % 3 ==2){
                                index++;
                            }
                        }else{
                            if(groups.size()<=s / 3){
                                continue;
                            }
                            maps = groupByName.get(groups.get(s / 3));

                            if(maps.size()>(row-lastMaxLength-2)){
                                xwpfRun.setText(String.valueOf(maps.get(row-lastMaxLength-2).get(pair.getRight())));
                            }else{
                                xwpfRun.setText("-");
                            }
                        }
                    }

                }

                row++;
            }
            lastMaxLength += maxLength+2;
        }

        // �������ȡ��߿����ʽ
        TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, 9);
        TableTools.borderTable(table, 4);

//         TODO ����XWPFTable API�������data������԰�����������Ҫ�����ݣ�����ͼƬ�ı���
//         TODO ����MiniTableRenderPolicy.Helper.renderRow�������ٷ������Ⱦһ������
//         TODO ����TableTools�෽��������񣬱���ϲ���Ԫ��
        // ......
//        TableTools.mergeCellsHorizonal(table, 0, 0, 7);
//        TableTools.mergeCellsVertically(table, 0, 1, 9);
    }
}
