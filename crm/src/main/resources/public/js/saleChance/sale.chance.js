layui.use(['table','layer','form'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        form = layui.form,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 营销机会列表展示
     */
    var  tableIns = table.render({
        elem: '#saleChanceList', // 表格绑定的ID
        url : ctx + '/sale_chance/list', // 访问数据的地址
        cellMinWidth : 95,
        page : true, // 开启分页
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",  //设置头部工具栏
        id : "saleChanceListTable", //给当前渲染的方式一个id
        cols : [[  //设置表头信息
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'chanceSource', title: '机会来源',align:"center"},
            {field: 'customerName', title: '客户名称',  align:'center'},
            {field: 'cgjl', title: '成功几率', align:'center'},
            {field: 'overview', title: '概要', align:'center'},
            {field: 'linkMan', title: '联系人',  align:'center'},
            {field: 'linkPhone', title: '联系电话', align:'center'},
            {field: 'description', title: '描述', align:'center'},
            {field: 'createMan', title: '创建人', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'assignMan', title: '指派人', align:'center'},
            {field: 'assignTime', title: '分配时间', align:'center'},
            {field: 'state', title: '分配状态', align:'center',templet:function(d){
                    return formatterState(d.state);
                }},
            {field: 'devResult', title: '开发状态', align:'center',templet:function (d) {
                    return formatterDevResult(d.devResult);
                }},
            {title: '操作', templet:'#saleChanceListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });

    /**
     * 格式化分配状态
     *  0 - 未分配
     *  1 - 已分配
     *  其他 - 未知
     * @param state
     * @returns {string}
     */
    function formatterState(state){
        if(state==0) {
            return "<div style='color: yellow'>未分配</div>";
        } else if(state==1) {
            return "<div style='color: green'>已分配</div>";
        } else {
            return "<div style='color: red'>未知</div>";
        }
    }

    /**
     * 格式化开发状态
     *  0 - 未开发
     *  1 - 开发中
     *  2 - 开发成功
     *  3 - 开发失败
     * @param value
     * @returns {string}
     */
    function formatterDevResult(value){
        if(value == 0) {
            return "<div style='color: yellow'>未开发</div>";
        } else if(value==1) {
            return "<div style='color: #00FF00;'>开发中</div>";
        } else if(value==2) {
            return "<div style='color: #00B83F'>开发成功</div>";
        } else if(value==3) {
            return "<div style='color: red'>开发失败</div>";
        } else {
            return "<div style='color: #af0000'>未知</div>"
        }
    }


    /**
     * 数据表格重新加载
     */
    $("#btnSearch").click(function(){
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                customerName:$('[name = "customerName"]').val(),
                createMan:$('[name = "createMan"]').val(),
                state:$('[name = "state"]').val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });



    /**
     * 监听头部工具栏的事件
     */
    //监听事件
    table.on('toolbar(saleChances)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        console.log(obj);
        switch(obj.event){
            case 'add':
                openAddOrUpdate(); //打开添加的窗口
                break;
            case 'del':
                layer.msg('删除');
                // 点击删除按钮，将对应选中的记录删除
                deleteSaleChance(checkStatus.data);
                break;
        };
    });

    /**
     * 删除营销机会数据
     * @param data
     */
    function deleteSaleChance(data) {
       // 判断用户是否选择了要删除的记录
        if (data.length == 0) {
            layer.msg("请选择要删除的记录！");
            return;
        }
       // 询问用户是否确认删除
        layer.confirm("您确定要删除选中的记录吗？", {
            btn: ["确认", "取消"],
        }, function (index) {
       // 关闭确认框
            layer.close(index);
       // ids=1&ids=2&ids=3
            var ids = "ids=";
        // 遍历获取对应的id
            for (var i = 0; i < data.length; i++) {
                if (i < data.length - 1) {
                    ids = ids + data[i].id + "&ids=";
                } else {
                    ids = ids + data[i].id;
                }
            }
        // 发送ajax请求，删除记录
            $.ajax({
                type: "post",
                url: ctx + "/sale_chance/delete",
                data: ids, // 参数传递的是数组
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                    // 加载表格
                        tableIns.reload();
                    } else {
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            });
        });
    }

        /**
     * 打开添加的窗口页面
     */
    function openAddOrUpdate() {
        //定义弹出框标题
        var title = "<h3>营销机会管理 - 机会添加</h3>";
        //请求后台显示页面地址
        var url = ctx + "/sale_chance/toAddUpdatePage";

        layer.open({
            type:2,   //弹出框的类型
            title:title,
            content:url, //弹出层的页面内容
            area:["500px","620px"],     //宽高
            maxmin:true //最大最小化
        });
    }

    /**
     * 监听行工具栏的事件
     */
    //监听事件
    table.on('tool(saleChances)', function(obj){
        console.log(obj);
        var data=obj.data;
        switch(obj.event){
            case 'edit':
                openUpdate(obj.data.id); //打开添加的窗口
                break;
            case 'del':
                layer.msg('删除');
                // 询问是否确认删除
                layer.confirm("确定要删除这条记录吗？", {icon: 3, title:"营销机会数据管理"}, function (index) {
                    // 关闭窗口
                        layer.close(index);
                   // 发送ajax请求，删除记录
                        $.ajax({
                            type:"post",
                            url: ctx + "/sale_chance/delete",
                            data:{
                                ids:data.id
                            },
                            dataType:"json",
                            success:function (result) {
                                if (result.code == 200) {
                               // 加载表格
                                    tableIns.reload();
                                } else {
                                    layer.msg(result.msg, {icon: 5});
                                }
                            }
                        });
                    });
                break;
        };
    });
    /**
     * 打开更新的窗口页面
     */
    function openUpdate(id) {
        //定义弹出框标题
        var title = "<h3>营销机会管理 - 机会更新</h3>";
        //请求后台显示页面地址
        var url = ctx + "/sale_chance/toUpdate/"+id;
       console.log("后台更新"+url);
        layer.open({
            type:2,   //弹出框的类型
            title:title,
            content:url, //弹出层的页面内容
            area:["500px","620px"],     //宽高
            maxmin:true //最大最小化
        });
    }

});