//初始化Excel导入的文件
function InitExcelFile() {
    //记录GUID
    $("#AttachGUID").val(newGuid());

    $("#excelFile").fileinput({
        uploadUrl: "/upload",//上传的地址
        uploadAsync: true,              //异步上传
        language: "zh",                 //设置语言
        showCaption: true,              //是否显示标题
        showUpload: true,               //是否显示上传按钮
        showRemove: true,               //是否显示移除按钮
        showPreview : true,             //是否显示预览按钮
        browseClass: "btn btn-primary", //按钮样式
        dropZoneEnabled: false,         //是否显示拖拽区域
        allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀
        maxFileCount: 1,                        //最大上传文件数限制
        previewFileIcon: '<i class="glyphicon glyphicon-file"></i>',
        allowedPreviewTypes: null,
        previewFileIconSettings: {
            'docx': '<i class="glyphicon glyphicon-file"></i>',
            'xlsx': '<i class="glyphicon glyphicon-file"></i>',
            'pptx': '<i class="glyphicon glyphicon-file"></i>',
            'jpg': '<i class="glyphicon glyphicon-picture"></i>',
            'pdf': '<i class="glyphicon glyphicon-file"></i>',
            'zip': '<i class="glyphicon glyphicon-file"></i>',
        }
        /*,
        uploadExtraData: {  //上传的时候，增加的附加参数
            folder: '数据导入文件', guid: $("#AttachGUID").val()
        }*/
    })  //文件上传完成后的事件
        .on('fileuploaded', function (event, data, previewId, index) {
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;

            var res = data.response; //返回结果
            if (res.Success) {
                showTips('上传成功');
                var guid = $("#AttachGUID").val();

                //提示用户Excel格式是否正常，如果正常加载数据
                $.ajax({
                    url: '/TestUser/CheckExcelColumns?guid=' + guid,
                    type: 'get',
                    dataType: 'json',
                    success: function (data) {
                        if (data.Success) {
                            InitImport(guid); //重新刷新表格数据
                            showToast("文件已上传，数据加载完毕！");

                            //重新刷新GUID，以及清空文件，方便下一次处理
                            RefreshExcel();
                        }
                        else {
                            showToast("上传的Excel文件检查不通过。请根据页面右上角的Excel模板格式进行数据录入。", "error");
                        }
                    }
                });
            }
            else {
                showTips('上传失败');
            }
        });
}