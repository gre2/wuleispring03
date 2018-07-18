package com.wl.beans.reader;

import com.wl.beans.beanDef.BeanDefinition;
import com.wl.beans.beanDef.PropertyValue;
import com.wl.beans.resour.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader){
        super(resourceLoader);
    }



    public void loadBeanDefinitions(String location) {
        InputStream inputStream=getResourceLoader().getResource(location).getInputStream();
        doLoadBeanDefinitions(inputStream);
    }

    private void doLoadBeanDefinitions(InputStream inputStream) {
        Document doc=getDocument(inputStream);
        parseBeanDefinitions(doc.getDocumentElement());
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseBeanDefinitions(Element element) {
        NodeList nodeList=element.getChildNodes();
        for(int i=0;i<nodeList.getLength();i++){
            Node node=nodeList.item(i);
            //todo  不知道为什么要有这个判断
            if(node instanceof Element){
                Element elementNode=(Element) node;
                processBeanDefinition(elementNode);
            }
        }

    }

    private void processBeanDefinition(Element elementNode) {
        String name=elementNode.getAttribute("id");
        String className=elementNode.getAttribute("class");
        BeanDefinition beanDefinition=new BeanDefinition();
        processProperty(elementNode,beanDefinition);
        beanDefinition.setBeanClassName(className);
        getRegistry().put(name,beanDefinition);
    }

    private void processProperty(Element element, BeanDefinition beanDefinition) {
        NodeList propertyNode =element.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if(node instanceof Element){
                Element propertyEle=(Element)node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if(value!=null){
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name,value));
                }
            }
        }

    }

    private Document getDocument(InputStream inputStream) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputStream);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
