(function ($) {
    $.fn.ajaxImageUpload = function (options) {

        var defaults = {

            staticPath:'',
            id:'',
            data: null,
            url: '',
            zoom: true,
            allowType: ["gif", "jpeg", "jpg", "bmp", 'png'],
            maxNum: 5,
            maxSize: 1, //设置允许上传图片的最大尺寸，单位M
            success: $.noop, //上传成功时的回调函数
            error: $.noop, //上传失败时的回调函数
            delete:$.noop

        };

        var thisObj = $(this);
        var config = $.extend(defaults, options);
         var boxID = config.id+"_uploadbox";
         var imageBoxId = config.id+"_imagebox";
        var uploadBox = $("#"+boxID);
        var imageBox = $("#"+imageBoxId);
        var inputName = thisObj.attr('name');
        var staticPath = config.staticPath;
        // var sectionId = config.id+"_section";
        // var imageShowId = config.id+"_show";

        // 设置是否在上传中全局变量
        isUploading = false;

        thisObj.each(function (i) {
            thisObj.change(function () {
                handleFileSelect();
            });
        });

        var handleFileSelect = function () {

            if (typeof FileReader == "undefined") {
                return false;
            }

            // 获取最新的section数量

            var imageNum = imageBox.children(".image-section").length;

            console.log("imageNum",imageNum);

            var postUrl = config.url;
            var maxNum = config.maxNum;
            var maxSize = config.maxSize;
            var allowType = config.allowType;

            if (!postUrl) {
                alert('请设置要上传的服务端地址');
                return false;
            }

            if (imageNum + 1 > maxNum) {
                alert("上传图片数目不可以超过" + maxNum + "个");
                return;
            }

            var files = thisObj[0].files;
            var fileObj = files[0];

            if (!fileObj) {
                return false;
            }

            var fileName = fileObj.name;
            var fileSize = (fileObj.size) / (1024 * 1024);

            if (!isAllowFile(fileName, allowType)) {

                alert("图片类型必须是" + allowType.join("，") + "中的一种");
                return false;

            }

            if (fileSize > maxSize) {

                alert('上传图片不能超过' + maxSize + 'M，当前上传图片的大小为' + fileSize.toFixed(2) + 'M');
                return false;

            }

            if (isUploading == true) {

                alert('文件正在上传中，请稍候再试！');
                return false;

            }

            // 将上传状态设为正在上传中
            isUploading = true;

            // 执行前置函数
            var callback = config.before;


            // console.log("selectSectionId:",$(this).attr("id"));

            if (callback && callback() === false) {
                return false;
            }

            createImageSection();

            ajaxUpload();

        };

        var ajaxUpload = function () {

            // 获取最新的
            var imageSection = imageBox.children('.image-section:first');
            var imageShow =  imageSection.find('.image-show');
            console.log("imageShow",imageShow.html());
            var formData = new FormData();

            var fileData = thisObj[0].files;

            if (fileData) {

                // 目前仅支持单图上传
                formData.append(inputName, fileData[0]);

            }

            var postData = config.data;

            if (postData) {
                for (var i in postData) {

                    formData.append(i, postData[i]);

                }
            }

            // ajax提交表单对象
            $.ajax({
                url: config.url,
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (200 != result.code) {
                        alert("服务器处理异常!");
                        imageSection.remove();
                        return false;
                    }
                    var json = $.parseJSON(result.ext);
                    console.log(json);
                    if (!json.src) {
                        alert('服务器返回的json数据中必须包含src元素');
                        imageSection.remove();
                        return false;
                    }

                    imageSection.removeClass("image-loading");
                    imageShow.removeClass("image-opcity");

                    imageShow.attr('src', json.src);
                    imageShow.siblings('input').val(json.src);

                    // 将上传状态设为非上传中
                    isUploading = false;

                    // 执行成功回调函数
                    var callback = config.success;
                    callback(json);

                },
                error: function (e) {

                    imageSection.remove();
                    // 执行失败回调函数
                    var callback = config.error;
                    callback(e);

                }
            });

        };

        var createDeleteModal = function () {

            var deleteModal = $("<aside class='delete-modal'><div class='modal-content'><p class='modal-tip'>您确定要删除作品图片吗？</p><p class='modal-btn'> <span class='confirm-btn'>确定</span><span class='cancel-btn'>取消</span></p></div></aside>");
            // 创建删除模态框
            deleteModal.appendTo(imageBox);

            var  deleteId = 0;
            // 显示弹框
            imageBox.delegate(".image-delete", "click", function () {

                // 声明全局变量
                deleteImageSection = $(this).parent();
                deleteId = $(this).attr("id");

                console.log("deleteId",deleteId);
                deleteModal.show();

            });

            // 确认删除
            $(".confirm-btn").click(function () {

                if(typeof deleteId != 'undefined'){
                var callback = config.delete;
                if(false === callback(deleteId)){
                    return false;
                }
                }
                deleteImageSection.remove();

                deleteModal.hide();

            });

            // 取消删除
            $(".cancel-btn").click(function () {
                deleteModal.hide();
            });

        };

        var createImageSection = function () {

            var imageSection = $("<section class='image-section image-loading'></section>");
            var imageShade = $("<div class='image-shade'></div>");
            var imageShow = $("<img class='image-show image-opcity' />");
            var imageInput = $("<input class='" + inputName + "' name='" + inputName + "[]' value='' type='hidden'>");
            var imageZoom = $("<div class='image-zoom'></div>");
            var imageDelete = $("<div class='image-delete'></div>");

            imageBox.prepend(imageSection);

            imageShade.appendTo(imageSection);
            imageDelete.appendTo(imageSection);
            // 判断是否开启缩放功能
            if (config.zoom && config.zoom === true) {
                imageZoom.appendTo(imageSection);
            }

            imageShow.appendTo(imageSection);
            imageInput.appendTo(imageSection);

            return imageSection;

        };

        var createImageZoom = function () {
            // console.log("staticPath",staticPath);

            var zoomShade = $("<div id='zoom-shade'></div>");
            var zoomBox = $("<div id='zoom-box'></div>");
            var zoomContent = $("<div id='zoom-content'>" +
                "<img src='"+staticPath+"/xiaodian/images/back.jpg'>" +
                "</div>");

            uploadBox.append(zoomShade);
            uploadBox.append(zoomBox);

            zoomContent.appendTo(zoomBox);

            // 显示弹框
            var childrenSelector = 'section.image-section  div.image-zoom';
            imageBox.delegate(childrenSelector, "click", function () {
                console.log("delegate",$(this));
                var src = $(this).siblings('img').attr('src');
                console.log("img-src",src);
                console.log("zoomBox",zoomBox);
                zoomBox.find('img').attr('src', src);

                $("#dummybodyid").removeAttr("style");
                uploadBox.find("#zoom-shade").show();
                uploadBox.find("#zoom-box").show();

                // zoomShade.show();
                // zoomBox.show();

            });

            // 关闭弹窗
            uploadBox.delegate("div#zoom-shade", "click", function () {

                zoomShade.hide();
                zoomBox.hide();

            });

        };

        //获取上传文件的后缀名
        var getFileExt = function (fileName) {
            if (!fileName) {
                return '';
            }

            var _index = fileName.lastIndexOf('.');
            if (_index < 1) {
                return '';
            }

            return fileName.substr(_index + 1);
        };

        //是否是允许上传文件格式
        var isAllowFile = function (fileName, allowType) {

            var fileExt = getFileExt(fileName).toLowerCase();
            if (!allowType) {
                allowType = ['jpg', 'jpeg', 'png', 'gif', 'bmp'];
            }

            if ($.inArray(fileExt, allowType) != -1) {
                return true;
            }
            return false;

        };

        // 判断是否开启缩放功能
        if (config.zoom && config.zoom === true) {
            createImageZoom();
        }

        createDeleteModal();

    };

})(jQuery);