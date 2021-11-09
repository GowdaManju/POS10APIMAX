package com.Pos10Max.POS10APIMAX;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class Pos10ApimaxApplicationTests {

	Calculater underTest=new Calculater();

	@Test
	void itShouldAddNumbers() {

		//given
		int a=20;
		int b=30;

		//when
		int result=underTest.add(a,b);

		//then
		int expected=50;
		assertThat(result).isEqualTo(expected);
	}

	class Calculater{
		int add(int a,int b){
			return a+b;
		}
	}

}
