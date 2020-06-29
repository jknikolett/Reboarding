package com.grape.reboarding.boarding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String imageUrl;
    private Integer position;
    private ResponseStatus status;
    private String message;

    public static ResponseDTOBuilder builder() {
        return new ResponseDTOBuilder(){
            @Override
            public ResponseDTO build() {
                if(super.position == null || super.position == 0){
                    position(null);
                    status(ResponseStatus.OK);
                }
                return super.build();
            }
        };
    }
}
