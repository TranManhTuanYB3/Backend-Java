package vn.eledevo.vksbe.constant;

public class ResponseMessage {

    private ResponseMessage() {}

    public static final String USER_EXIST = "Người dùng đã tồn tại";

    public static final String USER_BLANK = "Tên đăng nhập không được để trống";

    public static final String USER_SIZE = "Tên đăng nhập phải có độ dài từ 3 đến 50 ký tự";

    public static final String DEVICE_EXIST = "Thiết bị đã tồn tại";

    public static final String DEVICE_INFO_BLANK = "Tên thiết bị không được để trống";

    public static final String DEVICE_INFO_UUID_BLANK = "Mã UUID thiết bị không được để trống";

    public static final String CUSTOMER_EXIST = "Khách hàng đã tồn tại";

    public static final String CUSTOMER_NOT_EXIST = "Khách hàng không tồn tại";

    public static final String EMPLOYEE_EXIST = "Nhân viên đã tồn tại";

    public static final String EMPLOYEE_NOT_EXIST = "Nhân viên không tồn tại";

    public static final String ORDER_BLANK = "Khách hàng và nhân viên không được để trống";

    public static final String ORDER_NOT_EXIST = "Đơn hàng không tồn tại";

    public static final String PHONE_EXIST = "Số điện thoại đã tồn tại";
}
