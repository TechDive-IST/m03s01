package org.techdive.dao;

import org.techdive.model.Video;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@RequestScoped
@Transactional
public class VideosDao implements Serializable {

    @PersistenceContext(unitName = "VIDEOPU")
    EntityManager em;

    public List<Video> obter() {
        List<Video> videos = em.createQuery("SELECT v FROM Video v", Video.class).getResultList();
        return videos;
    }

}
