package com.hjc.server.service;

import com.hjc.server.domain.Section;
import com.hjc.server.domain.SectionExample;
import com.hjc.server.dto.SectionDto;
import com.hjc.server.dto.SectionPageDto;
import com.hjc.server.mapper.SectionMapper;
import com.hjc.server.util.CopyUtil;
import com.hjc.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private CourseService courseService;

    /**
     * 列表查询
     */
    public void list(SectionPageDto sectionPageDto) {
        PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if (!StringUtils.isEmpty(sectionPageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        sectionExample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        sectionPageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        sectionPageDto.setList(sectionDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    /**
     * 新增
     */
    private void insert(Section section) {
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    /**
     * 更新
     */
    private void update(Section section) {
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询某一课程下的所有节
     */
    public List<SectionDto> listByCourse(String courseId) {
        SectionExample example = new SectionExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<Section> sectionList = sectionMapper.selectByExample(example);
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        return sectionDtoList;
    }
}
