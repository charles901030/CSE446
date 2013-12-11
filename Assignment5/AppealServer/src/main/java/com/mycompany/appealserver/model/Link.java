/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;
 
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author shaosh
 */
@XmlRootElement(namespace = Uri.DAP_NAMESPACE)
public class Link {
    @XmlAttribute(name="rel")
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
        
        this.rel = Uri.RELURI + "/" + rel;
        this.uri = uri.toString();
        this.mediaType = mediaType;
    }
    
    public String getRel(){
        return rel;
    }
    
    public URI getUri() throws URISyntaxException{
        return new URI(uri);
    }
    
    public String getMediaType(){
        return mediaType;
    }
}
