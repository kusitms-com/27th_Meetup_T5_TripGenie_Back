package com.simpletripbe.modulecommon.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ToString
public class ApiResponse<T> {
    private final CommonCode code;
    private final String message;
    private final T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PageResponse page;

    private ApiResponse(CommonCode code, String message, T data, PageResponse page) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.page = page;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                CommonCode.SUCCESS,
                CommonCode.SUCCESS.getMessage(),
                null,
                null
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                CommonCode.SUCCESS,
                CommonCode.SUCCESS.getMessage(),
                data,
                null
        );
    }

    public static <T> ApiResponse<List<T>> success(List<T> data) {
        return new ApiResponse<>(
                CommonCode.SUCCESS,
                CommonCode.SUCCESS.getMessage(),
                data,
                null
        );
    }

    public static <T> ApiResponse<List<T>> success(Page<T> data) {
        return new ApiResponse<>(
                CommonCode.SUCCESS,
                CommonCode.SUCCESS.getMessage(),
                data.getContent(),
                PageResponse.from(data)
        );
    }

    public static <T> ApiResponse<T> failure(CommonCode CommonCode) {
        return new ApiResponse<>(
                CommonCode,
                CommonCode.getMessage(),
                null,
                null
        );
    }

    public static <T> ApiResponse<T> failure(CommonCode CommonCode, String message) {
        return new ApiResponse<>(
                CommonCode,
                message,
                null,
                null
        );
    }

    @Getter
    @ToString
    public static class PageResponse {
        private final int number;
        private final int size;
        private final int totalCount;

        private PageResponse(int number, int size, int totalCount) {
            this.number = number;
            this.size = size;
            this.totalCount = totalCount;
        }

        public static PageResponse from(Page<?> page) {
            return new PageResponse(
                    page.getNumber(),
                    page.getSize(),
                    (int)page.getTotalElements()
            );
        }
    }
}
