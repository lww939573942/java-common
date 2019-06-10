package cn.company.common.utils;

import cn.company.common.bean.ApiResponse;

/**
 * @author donaldhan
 * 接口返回工具类
 */
public class ResponseUtils {
    private static ThreadLocal<ApiResponse> responseThreadLocal = new ThreadLocal<>();

    public static ApiResponse setResult() {
        ApiResponse apiResponse = new ApiResponse();
        responseThreadLocal.set(apiResponse);

        return apiResponse;
    }

    public static ApiResponse setResult(Object data) {
        ApiResponse apiResponse = new ApiResponse();

        if (ValidateUtils.isEmpty(data)) {
            apiResponse.setMessage("找不到数据");
        } else {
            apiResponse.setData(data);
        }
        responseThreadLocal.set(apiResponse);

        return apiResponse;
    }

    public static ApiResponse setResult(boolean isSuccess) {
        ApiResponse apiResponse = null;
        if (isSuccess) {
            apiResponse = new ApiResponse(0, "操作成功");
        } else {
            apiResponse = new ApiResponse(1, "操作失败");
        }
        responseThreadLocal.set(apiResponse);

        return apiResponse;
    }

    public static ApiResponse setResult(Integer code, String message) {
        ApiResponse apiResponse = new ApiResponse(code, message);
        responseThreadLocal.set(apiResponse);

        return apiResponse;
    }

    public static ApiResponse setResult(Integer code, String message, Object data) {
        ApiResponse apiResponse = new ApiResponse(code, message);

        if (ValidateUtils.isNotEmpty(data)) {
            apiResponse.setData(data);
        }

        responseThreadLocal.set(apiResponse);

        return apiResponse;
    }

    public static ApiResponse getResult() {
        return responseThreadLocal.get();
    }

    public static void remove() {
        responseThreadLocal.remove();
    }
}
