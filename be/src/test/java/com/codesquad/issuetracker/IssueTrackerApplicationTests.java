package com.codesquad.issuetracker;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IssueTrackerApplicationTests {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    void contextLoads() {
        String encrypt = stringEncryptor.encrypt("24c5a7cbebdb0fc0c78f98e2f2cb10f0ce366d59");
        System.out.println("암호화된 내용 = " + encrypt);

        //복호화
        String decryptedString = stringEncryptor.decrypt(
                "GCxz5azwg+WmshpvuitkvrFFKknKMBUCaqJiQGIWPC/yFyo5o1rsYRREF2ntQduqLVbImzjDUOI=");
        System.out.println("복호화된 내용 = " + decryptedString);
    }

}
