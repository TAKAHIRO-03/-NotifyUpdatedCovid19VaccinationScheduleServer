package jp.co.tk.nucvs.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.tk.nucvs.domain.service.covid19.ReqAdachikuServiceImpl;

@SpringBootTest
public class ReqAdachikuServiceImplTests {

    @Autowired
    ReqAdachikuServiceImpl service;

    @BeforeEach
    void 関数実行前() {

    }

    @Test
    void 実行確認() throws Exception {
        service.request();
    }

}
