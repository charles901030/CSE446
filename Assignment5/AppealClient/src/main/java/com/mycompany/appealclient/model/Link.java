/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;
 
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shaosh
 */
@XmlRootElement
public class Link {
    @XmlAttribute(name="rel")
//    private String fullrel;
//    @XmlTransient
    private String rel;
    @XmlAttribute(name="uri")
    private String uri;
    @XmlAttribute(name="mediaType")
    private String mediaType;
    
    Link(){}
    
    public Link(String rel, URI uri, String mediaType){
//        LOG.info("Creating a Link object");
//        LOG.debug("rel = " + name);
//        LOG.debug("uri = " + uri);
//        LOG.debug("mediaType = " + mediaType);
        
//        this.fullrel = Uri.RELURI + rel;
        this.rel = Uri.RELURI + rel;
        this.uri = uri.toString();
        this.mediaType = mediaType;
    }
    
    @XmlTransient
    public String getRel(){
        return rel;
    }
    
//    public String getFullrel(){
//        return fullrel;
//    }
    
    @XmlTransient
    public URI getUri() throws URISyntaxException{
        return new URI(uri);
    }
    
    @XmlTransient
    public String getMediaType(){
        return mediaType;
    }
    
    public String toString(){
        return "Link: rel = " + rel + " uri = " + uri + " mediatype = " + mediaType + "\n";  
    }
}
