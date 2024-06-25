package com.ms.custormer.handler;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {

}
