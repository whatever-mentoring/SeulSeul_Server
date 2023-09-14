package com.seulseul.seulseul.service.test;

import com.seulseul.seulseul.dto.test.TestDto;
import com.seulseul.seulseul.dto.test.TestUpdateDto;
import com.seulseul.seulseul.entity.test.Test;
import com.seulseul.seulseul.repository.test.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<TestDto> getTests() {
        List<Test> tests = testRepository.findAll();
        return tests.stream().map(TestDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTest(String id) {
        Test test = testRepository.findById(id);
        testRepository.delete(test);
    }

    @Transactional
    public void updateTest(TestUpdateDto dto) {
        Test test = testRepository.findById(dto.getId());
        test.updateTitle(dto.getTitle(), dto.getBody());
    }
}
