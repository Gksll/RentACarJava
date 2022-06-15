package com.kodlamaio.rentACar.Core.webservices;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class FindexServiceAdapter {
	Random random = new Random();
	public int CheckFindexScore(String tcNo) {
		
		int score = random.nextInt(1, 1900);
		System.out.println(score);
	    	return score;
	}
}
