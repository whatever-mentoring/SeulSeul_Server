package com.seulseul.seulseul.service.test;

import com.seulseul.seulseul.dto.test.TestDto;
import com.seulseul.seulseul.entity.test.Test;
import com.seulseul.seulseul.repository.test.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    private TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Transactional
    public void save(TestDto testDto) {
        testRepository.save(new Test(testDto.getUserId(), testDto.getId(), testDto.getTitle(), testDto.getBody()));
    }
}
