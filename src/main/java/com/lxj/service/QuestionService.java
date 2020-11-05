package com.lxj.service;

import com.lxj.dto.PaginationDTO;
import com.lxj.dto.QuestionDTO;
import com.lxj.mapper.QuestionMapper;
import com.lxj.mapper.UserMapper;
import com.lxj.model.Question;
import com.lxj.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

//    public List<QuestionDTO> getQuestionDTOList(){
//        List<QuestionDTO> questionDTOList = null;
//        List<Question> questionList = questionMapper.selectAll();
//        if(questionList != null){
//            questionDTOList = new ArrayList<QuestionDTO>();
//            for (Question question : questionList) {
//                User user = userMapper.findOneById(question.getCreator());
//                QuestionDTO questionDTO = new QuestionDTO();
//                BeanUtils.copyProperties(question,questionDTO);
//                questionDTO.setUser(user);
//                questionDTOList.add(questionDTO);
//            }
//        }
//        return questionDTOList;
//    }

    public PaginationDTO getPaginationDTO(Integer currentPage, Integer pageSize) {
        Integer offset = (currentPage - 1) * pageSize;
        Integer rows = pageSize;
        List<QuestionDTO> questionDTOList = null;
        PaginationDTO paginationDTO = new PaginationDTO();
        List<Question> questionList = questionMapper.selectPagination(offset, rows);
        if (questionList != null) {
            questionDTOList = new ArrayList<QuestionDTO>();
            for (Question question : questionList) {
                User user = userMapper.findOneById(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }
        paginationDTO.setQuestionDTOS(questionDTOList);
        paginationDTO.setCurrentPage(currentPage);
        Integer counts = questionMapper.getCounts();
        paginationDTO.setTotalPage((int) Math.ceil(counts *1.0/ pageSize));

        paginationDTO.setPagination(currentPage,paginationDTO.getTotalPage());
        return paginationDTO;

    }
}
