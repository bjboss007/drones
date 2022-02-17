package com.musala.drone.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppResponse {
    private String message;
    private Object data;
}
