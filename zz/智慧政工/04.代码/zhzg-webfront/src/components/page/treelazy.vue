<template>
    <el-tree
        :props="props"
        :load="loadNode"
        lazy
        node-key="id"
        @node-click="haha"
        >
    </el-tree>
</template>

<script>
    export default {
        data() {
            return {
                props: {
                    label: 'name',
                    children: 'zones',
                    isLeaf: 'leaf'
                },
            };
        },
        methods: {
            haha(a,b,c){

                if(b.level==2){
                    console.log(a)
                    console.log(b)
                    console.log(c)
                }
            },
            loadNode(node, resolve) {
                console.log(node)
                if (node.level === 0) {
                    return resolve([{ name: '政治部',id:1},{ name: '政治部人事处' ,id:2},{ name: '政治部宣传处' ,id:3},{ name: '政治部组织教育处' ,id:4},]);
                }
                if (node.level > 1) return resolve([]);


                setTimeout(() => {
                    const data = [{
                        name: '王思'+node.data.id,
                        id:5+node.data.id,
                        leaf: true
                    }, {
                        name: '李飞'+node.data.id,
                        id:6+node.data.id,
                        leaf: true
                    }];

                    resolve(data);
                }, 500);
            }
        }
    };
</script>

<style scoped>

</style>
