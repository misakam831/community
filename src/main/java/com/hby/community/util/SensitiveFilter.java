package com.hby.community.util;

import lombok.Data;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    public static final String REPLACEMENT = "***";

    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                this.addKeyword(keyword);

            }

        } catch (IOException e) {
            logger.error("加载敏感词文件失败" + e.getMessage());
        }
    }

    //将敏感词加入前缀树
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                //初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            //指向子节点
            tempNode = subNode;

            //设置结束标志
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }

        }

    }

    /**
     * @param text 带过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        //指针一
        TrieNode tempNode = rootNode;
        //指针二
        int begin = 0;
        //指针三
        int positon = 0;

        StringBuffer sb = new StringBuffer();
        while (positon < text.length()) {
            char c = text.charAt(positon);

            //跳过符号
            if (isSymbol(c)) {
                //如果1在根节点,则直接跳过符号，让指针2向后移动
                if (tempNode == null) {
                    sb.append(c);
                    begin++;
                }
                positon++;
                continue;
            }

            //检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                //begin指向的字符
                sb.append(text.charAt(begin));
                //进入下一个位置
                positon = ++begin;
                //指针重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                //发现了敏感词，begin开头，position结尾
                sb.append(REPLACEMENT);
                //进入下一个位置
                begin = ++positon;
                //指针重新指向根节点
                tempNode = rootNode;
            } else {
                positon++;
            }
        }
        //最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();

    }

    //判断是是否为符号
    private boolean isSymbol(Character c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    //前缀树
    @Data
    private class TrieNode {
        //关键词结束的标志
        private boolean isKeywordEnd = false;

        //当前结点子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        //添加子节点的方法
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        //获取子节点的方法
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }
}
