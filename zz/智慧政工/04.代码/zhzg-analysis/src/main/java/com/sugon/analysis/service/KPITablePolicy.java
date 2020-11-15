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
 * @description: 自定义表格填充策略
 * @author: liuwei
 * @create: 2020-04-27 20:45
 **/
public class KPITablePolicy extends AbstractRenderPolicy<List<Map<String,Object >>> {
    @Override
    protected void afterRender(RenderContext<List<Map<String,Object >>> context) {
        // 清空标签
        clearPlaceholder(context, true);
    }

    List<Pair<String,String>> heads= Arrays.asList(Pair.of("考核单位","SIMPLECHINANAME"),Pair.of("考核成绩","SCORE"),Pair.of("排名","RN"));

    @Override
    public void doRender(RenderContext<List<Map<String,Object >>> context) throws Exception {
        List<Map<String, Object>> data = context.getData();
        List<String> groupNames = data.stream().map(p -> String.valueOf(p.get("GROUPNAME"))).distinct().collect(Collectors.toList());
        Map<String, List<Map<String, Object>>> groupByName = data.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("GROUPNAME")), Collectors.toList()));


        XWPFRun run = context.getRun();
        // 当前位置的容器
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
        // 当前位置插入表格
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
            //maxLength + 2  包含两个表头
            for (int m = 0; m < maxLength + 2; m++) {
                XWPFTableRow groupNameRow = table.insertNewTableRow(row);
                for (int n = 0; n < 9; n++) {
                    groupNameRow.createCell();
                }
                if(m==0){//考核单元行
                    if(groups.size()==1){
                        TableTools.mergeCellsHorizonal(table, row, 0, 8);

                        XWPFTableCell cell = groupNameRow.getCell(0);
                        cell.setColor("c2c2c2");
                        XWPFParagraph paragraph = cell.getParagraphs().get(0);
                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun xwpfRun = paragraph.createRun();
                        xwpfRun.setText(groups.get(0));
                        xwpfRun.setFontFamily("方正仿宋_GBK");
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
                            xwpfRun.setFontFamily("方正仿宋_GBK");
                            xwpfRun.setText(groups.get(s));
                        }

                    }
                }else if(m==1){//考核单位，考核成绩，排名行
                    for (int s = 0; s < 9; s++) {
                        XWPFTableCell cell = groupNameRow.getCell(s);
                        cell.setColor("c2c2c2");
                        Pair<String, String> pair = heads.get(s % 3);
                        XWPFParagraph paragraph = cell.getParagraphs().get(0);
                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun xwpfRun = paragraph.createRun();
                        xwpfRun.setFontSize(9);
                        xwpfRun.setFontFamily("方正仿宋_GBK");
                        xwpfRun.setText(pair.getLeft());
                    }
                }else{//数据

                    for (int s = 0; s < 9; s++) {
                        XWPFTableCell cell = groupNameRow.getCell(s);
                        XWPFParagraph paragraph = cell.getParagraphs().get(0);
                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun xwpfRun = paragraph.createRun();
                        xwpfRun.setFontSize(9);
                        xwpfRun.setFontFamily("方正仿宋_GBK");
                        Pair<String, String> pair = heads.get(s % 3);
                        List<Map<String, Object>> maps;
                        if(groups.size()==1){//单元数除3余1时，最后一个单元
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

        // 定义表格宽度、边框和样式
        TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, 9);
        TableTools.borderTable(table, 4);

//         TODO 调用XWPFTable API操作表格：data对象可以包含任意你想要的数据，包括图片文本等
//         TODO 调用MiniTableRenderPolicy.Helper.renderRow方法快速方便的渲染一行数据
//         TODO 调用TableTools类方法操作表格，比如合并单元格
        // ......
//        TableTools.mergeCellsHorizonal(table, 0, 0, 7);
//        TableTools.mergeCellsVertically(table, 0, 1, 9);
    }
}
