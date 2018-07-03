//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//=================================================================================================
@Configuration
public class ObjectMappersConfiguration {

	//---------------------------------------------------------------------------------------------
	@Autowired
	public ObjectMappersConfiguration(ObjectMapper objectMapper) {

		// configures jackson JSON serializer to pretty print its output
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	//---------------------------------------------------------------------------------------------
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
