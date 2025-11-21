package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WelletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final int THREAD_COUNT = 100;

    @Test
    void WithdrawTest() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        CountDownLatch ready = new CountDownLatch(THREAD_COUNT);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    ready.countDown();  // 준비 완료 표시
                    start.await();      // 모든 스레드가 준비될 때까지 대기

                    mockMvc.perform(post("/api/wallets/123/withdraw")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                              "transactionId": "test",
                                              "amount": 1000
                                            }
                                            """)
                            )
                            .andExpect(status().isOk());

                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    done.countDown();
                }
            });
        }

        ready.await();
        start.countDown();
        done.await();

        executorService.shutdown();

        System.out.println("/api/wallets/{walletId}/withdraw API 동시 호출 테스트 완료");
    }
}
