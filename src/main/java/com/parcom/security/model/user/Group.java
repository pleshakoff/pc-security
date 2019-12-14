package com.parcom.security.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Group {

   private Long id;


}
