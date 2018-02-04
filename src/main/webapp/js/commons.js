var modal_params = {
    backdrop: 'static',
    keyboard: false,
    show: false
};

// 通用提示框关闭
$('#modal-close-btn').click(function () {
    $('#message-modal').modal('hide');
});