package com.nowcoder.community.utils;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveFilter.class);

    // 用户替换敏感词
    private static final String REPLACEMENT = "***";

    // 初始化根节点
    private TireNode rootNode = new TireNode();

    @PostConstruct
    // @PostConstruct 修饰的方法会在服务器加载 Servlet 的时候运行，并且只会被服务器执行一次。
    // PostConstruct 在构造函数之后执行，init() 方法之前执行。
    public void init() {
        try (
                // 得到字节流
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                // 将字节流转成字符流，然后再将字符流转换成缓冲流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            // 不为空，说明还有数据，则继续读
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树
                addKeyword(keyword);
            }
        } catch (IOException e) {
            LOGGER.error("加载敏感词文件失败!" + e.getMessage());
        }
    }

    // 将敏感词添加到前缀树
    private void addKeyword(String keyword) {
        TireNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);

            // 这里先从前缀树中获取字符 c，如果获取到的 c 是 null，
            // 则说明前缀树中没有这个字符 c，那么就可以将 c 放进前缀树了
            TireNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                // 初始化子节点，将其添加到 tempNode 节点的后面
                subNode = new TireNode();
                tempNode.addSubNode(c, subNode);
            }

            // 指针后移
            tempNode = subNode;

            // 对最后一个节点设置结束的标识
            if (i == (keyword.length() - 1)) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词方法
     *
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        // 设置三个指针
        TireNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        // 用于存放最终的结果
        StringBuilder sb = new StringBuilder();
        // 以 position 作为条件
        while (position < text.length()) {
            char c = text.charAt(position);
            // 跳过特殊符号
            if (isSymbol(c)) {
                // tempNode 处于根节点，将此符号计入结果，让 begin 后移
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                // 无论符号在开头还是中间，position 都后移
                position++;
                continue;
            }
            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // 以 begin 开始的字符串不是敏感词，将其加入到 sb 中
                sb.append(text.charAt(begin));
                // 进入下一位置
                position = ++ begin;
                // 重新指向根节点
                tempNode = rootNode;
                // 如果发现了敏感词，则将 begin 到 position 之内的字符串进行替换
            } else if (tempNode.isKeywordEnd()){
                sb.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            } else {
                // 继续检查下一个字符
                position++;
            }
        }
        // 将最后一批字符记入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    // 判断是否是特殊符号
    public boolean isSymbol(Character c) {
        // isAsciiAlphanumeric() 普通字符的话返回 true
        // 0x2E80~0x9FFF 表示东亚文字
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }


    private class TireNode {

        // 敏感词结束的标志
        private boolean isKeywordEnd = false;

        // 定义当前节点的子节点
        // 其中 key 表示子节点的字符，value 表示子节点
        private Map<Character, TireNode> subNode = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TireNode node) {
            subNode.put(c, node);
        }

        // 获取子节点
        public TireNode getSubNode(Character c) {
            return subNode.get(c);
        }
    }
}
