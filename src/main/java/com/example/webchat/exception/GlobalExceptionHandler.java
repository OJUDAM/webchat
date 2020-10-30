package com.example.webchat.exception;

import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.webchat.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler( Exception.class )
	public void handleException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception{
		
		StringWriter errors = new StringWriter();
		
		String accept = request.getHeader("accept");
		if( accept.matches(".*application/json.*")){
			response.setStatus(HttpServletResponse.SC_OK);
			
			OutputStream out = response.getOutputStream();
			out.write(new ObjectMapper().writeValueAsString(JSONResult.fail(errors.toString())).getBytes("utf-8"));
			out.flush();
			out.close();
		}else {
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
	}
}
