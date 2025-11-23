package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;
import java.util.Random;
import java.util.UUID;
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
    private final List<String> WALLET_IDS = List.of();
    private final Random random = new Random();

    public String getWalletId() {
        return WALLET_IDS.get(random.nextInt(WALLET_IDS.size()));
    }

    @Test
    void WithdrawConcurrencyTest() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        CountDownLatch ready = new CountDownLatch(THREAD_COUNT);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(THREAD_COUNT);

        String walletId = "5b32769f-c768-11f0-a863-bef5164605b6";

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    ready.countDown();
                    start.await();

                    String transactionId = UUID.randomUUID().toString();

                    mockMvc.perform(post("/api/wallets/%s/withdraw".formatted(walletId))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                              "transactionId": "%s",
                                              "amount": 10000
                                            }
                                            """.formatted(transactionId))
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
