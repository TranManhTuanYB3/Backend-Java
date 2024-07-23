package vn.eledevo.vksbe.constant;

public class ResponseMessage {

    private ResponseMessage() {}

    public static final String USER_EXIST = "Người dùng đã tồn tại";

    public static final String USER_BLANK = "Tên đăng nhập không được để trống";

    public static final String USER_SIZE = "Tên đăng nhập phải có độ dài từ 3 đến 50 ký tự";

    public static final String DEVICE_EXIST = "Thiết bị đã tồn tại";

    public static final String DEVICE_INFO_BLANK = "Tên thiết bị không được để trống";

    public static final String DEVICE_INFO_UUID_BLANK = "Mã UUID thiết bị không được để trống";
}
