package com.newcoder.community.util;

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
    //日志
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    //替换符
    private static final String REPLACEMENT= "***";

    //根据敏感词初始化前缀树数据
    TrieNode root = new TrieNode();
    //该结构需要程序启动的时候初始化，且只需要初始化一次
    @PostConstruct //这是一个初始化方法，当容器调用这个bin时，构造函数运行完以后，执行这个方法。
    public void init(){
        try(
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ){
            String keyword;
            while((keyword = reader.readLine()) != null){
                this.addKeyword(keyword);
            }
        }catch (IOException e){
            logger.error("加载敏感词文件失败" + e.getMessage());
        }

    }

    //将一个敏感词添加到前缀树
    private void addKeyword(String keyword){
        TrieNode tmpNode = root;
        for(int i=0; i<keyword.length();i++){
            char ch = keyword.charAt(i);
            TrieNode subNode = tmpNode.getSubNode(ch);
            if(subNode == null){
                //初始化
                subNode = new TrieNode();
                tmpNode.addSubNode(ch,subNode);
            }
            //指针指向子节点
            tmpNode = subNode;
            //最后一个字符设置结束的标志
            if(i==keyword.length()-1){
                tmpNode.setKeyWordEnd(true);
            }
        }
    }

    /*
    * 过滤敏感词
    *
    * @Param text 待过滤的文本
    * @return 过滤后的文本
    * */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        //指针1 指向树根
        TrieNode  tempNode = root;
        //指针2,3 遍历字符串
        int begin = 0;
        int position = 0;
        //结果
        StringBuilder sb  = new StringBuilder();

        while(position < text.length()){
            char c = text.charAt(position);
            //跳过符号
            if(isSymbol(c)){
                //若指针1指向根节点，将此符号计入结果，让指针2前进一部
                if(tempNode == root){
                    sb.append(c);
                    begin++;
                }
                //无论符号在开头和中间，指针3都向下走一步
                position++;
                continue;
            }
            //检测是否是敏感字符
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                sb.append(text.charAt(begin));
                begin++;
                position=begin;
                //
                tempNode = root;
            }else if(tempNode.isKeyWordEnd()){
                sb.append(REPLACEMENT);
                position++;
                begin = position;
                tempNode = root;
            }else{
                position++;
            }

        }
        sb.append(text.substring(begin));
        return sb.toString();

    }

    //判断是否为符号
    //2E80 0X9FF 东亚的文字范围
    private  boolean isSymbol(Character c){
        return !CharUtils.isAsciiAlphanumeric(c) && (c<0x2E80 || c> 0x9FFF);
    }

    //前缀树结构
    private  class TrieNode{
        //关键词结束标志
        private boolean isKeyWordEnd = false;
        //子节点（key是下级字符， value是下级节点）
        private Map<Character, TrieNode>  subNodes = new HashMap<>();
        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
        //添加子节点
        public void addSubNode(Character ch, TrieNode val){
            subNodes.put(ch,val);
        }
        //添加子节点
        public TrieNode getSubNode(Character ch){
            return subNodes.get(ch);
        }
    }


}
