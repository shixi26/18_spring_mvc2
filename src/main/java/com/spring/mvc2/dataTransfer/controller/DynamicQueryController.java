package com.spring.mvc2.dataTransfer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.mvc2.dataTransfer.dao.DynamicQueryDao;
import com.spring.mvc2.dataTransfer.domain.OrderDto;

@Controller
@RequestMapping("dynamicQuery")
public class DynamicQueryController {
	
	@Autowired
	private DynamicQueryDao dynamicQueryDao;
	
	@RequestMapping(value="/ifEx", method=RequestMethod.GET)
	public String ifEx() {
		
		OrderDto orderDto = new OrderDto();
		orderDto.setProductCode("0x999"); // 주석 해지, 적용을 바꾸면서 확인
		orderDto.setProductName("ifEx테스트상품명");
		orderDto.setProductPrice(10000);
		orderDto.setOrderCount(9);
		orderDto.setTotalPrice(90000);
		dynamicQueryDao.ifEx(orderDto);
		
		return "home";
	}
	
	@RequestMapping(value="/chooseEx", method=RequestMethod.GET)
	public String chooseEx() {
		
		OrderDto orderDto = new OrderDto();
		orderDto.setProductCode("0x003"); //0x001 0x002 0x004 로 바꾸면서 확인
		orderDto.setOrderCount(3);
		
		dynamicQueryDao.chooseEx(orderDto);
		return "home";
	}
	
	@RequestMapping(value="/foreachEx" , method=RequestMethod.GET)
	public String foreachEx() {
		
		List<OrderDto> orderList = new ArrayList<OrderDto>();
		OrderDto orderDto = null;
		
		for (int i = 1; i < 11; i++) {
			orderDto = new OrderDto();
			orderDto.setMemberId("임시유저아이디" + i);
			orderDto.setOrderId("임시주문상품 아이디" + i);
			orderDto.setProductCode("임시상품코드" + i);
			orderDto.setProductName("임시상품명" + i);
			orderDto.setProductPrice(1000 * i);
			orderDto.setOrderCount(i);
			orderDto.setTotalPrice(orderDto.getProductPrice() * orderDto.getOrderCount());
			orderList.add(orderDto);
		}
		
		dynamicQueryDao.foreachEx(orderList);
		
		return "home";
	}
	
	//camelcase mybatis-config에없으면 에러나
	@RequestMapping(value="/whereEx", method=RequestMethod.GET)
	public String whereEx() {
		
		OrderDto orderDto = new OrderDto();
		
		//1) memberId 와 orderId 가 모두 잇을 경우 > 정상
		//orderDto.setMemberId("user1");
		//orderDto.setOrderId("req1");
		
		//2) memberId만 있을 경우 > 정상
		//orderDto.setMemberId("user2");
		
		//3) orderId만 있을 경우 > 에러
		orderDto.setOrderId("req3");
		
		dynamicQueryDao.whereEx(orderDto);
		
		return "home";
	}
	
	@RequestMapping(value="/setEx", method=RequestMethod.GET)
	public String setEx() {
		
		OrderDto orderDto = new OrderDto();
		orderDto.setProductCode("0x003");
		orderDto.setProductName("(신제품)장패드");
		orderDto.setProductPrice(0);
		
		dynamicQueryDao.setEx(orderDto);
		
		return "home";
	}
}
