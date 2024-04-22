package com.oracle.team2.service.JY_Service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JY_Paging {
	
	private int currentPage = 1;
	private int rowPage = 5;
	private int pageBlock = 10;
	private int start;
	private int end;
	private int startPage;
	private int endPage;
	private int total;
	private int totalPage;

	public JY_Paging(int total, String currentPage1) {
		this.total = total;
		if (currentPage1 != null) {
			this.currentPage = Integer.parseInt(currentPage1); 
		}

		start = (currentPage - 1) * rowPage + 1; 
		end = start + rowPage - 1;                    

		totalPage = (int) Math.ceil((double)total / rowPage); 

		startPage = currentPage - (currentPage - 1) % pageBlock;
		endPage = startPage + pageBlock - 1; 

		if (endPage > totalPage) {
			endPage = totalPage;
		}
	}

}
