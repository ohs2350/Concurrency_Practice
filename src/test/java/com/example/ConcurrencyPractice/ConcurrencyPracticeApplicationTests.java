package com.example.ConcurrencyPractice;

import com.example.ConcurrencyPractice.model.Product;
import com.example.ConcurrencyPractice.service.BasicProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@SpringBootTest
class ConcurrencyPracticeApplicationTests {

	@Autowired
	BasicProductService basicProductService;


	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("동시성 문제가 있는 상품 조회수")
	void viewProductWithProblem() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		CountDownLatch countDownLatch = new CountDownLatch(100);
		AtomicInteger count = new AtomicInteger();
		Long testId = 45L;

		Long views = basicProductService.getViews(testId);
		IntStream.range(0, 100).forEach(e -> executorService.submit(() -> {
					try {
						basicProductService.viewProductWithProblem(testId);
						count.getAndIncrement();
					} finally {
						countDownLatch.countDown();
					}
				}
		));
		countDownLatch.await();
		Long result = (basicProductService.getViews(testId)) - views;

		System.out.println("총 조회 횟수 : " + count.get());
		System.out.println("실제 증가한 조회 수 : " + result);
	}

	@Test
	@DisplayName("비관적 락 사용 - SQL에서 FOR UPDATE로 구현 즉, X-Lock")
	void viewProductWithPessimistic() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		CountDownLatch countDownLatch = new CountDownLatch(100);
		AtomicInteger count = new AtomicInteger();
		Long testId = 45L;

		Long views = basicProductService.getViews(testId);
		IntStream.range(0, 100).forEach(e -> executorService.submit(() -> {
					try {
						basicProductService.viewProductWithPessimistic(testId);
						count.getAndIncrement();
					} finally {
						countDownLatch.countDown();
					}
				}
		));
		countDownLatch.await();
		Long result = (basicProductService.getViews(testId)) - views;

		System.out.println("총 조회 횟수 : " + count.get());
		System.out.println("실제 증가한 조회 수 : " + result);
	}

	@Test
	@DisplayName("For Update(베타적 락) 사용한 조회 테스트")
	void viewProductWithForUpdate() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		CountDownLatch countDownLatch = new CountDownLatch(100);
		AtomicInteger count = new AtomicInteger();
		Long testId = 45L;

		Long views = basicProductService.getViews(testId);
		IntStream.range(0, 100).forEach(e -> executorService.submit(() -> {
					try {
						basicProductService.viewProductWithUpdateQuery(testId);
						count.getAndIncrement();
					} finally {
						countDownLatch.countDown();
					}
				}
		));
		countDownLatch.await();
		Long result = (basicProductService.getViews(testId)) - views;

		System.out.println("총 조회 횟수 : " + count.get());
		System.out.println("실제 증가한 조회 수 : " + result);
	}
}
