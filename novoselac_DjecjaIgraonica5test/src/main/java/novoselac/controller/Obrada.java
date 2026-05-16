/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package novoselac.controller;

import java.util.List;
import novoselac.model.Entitet;
import novoselac.util.HibernateUtil;
import novoselac.util.NovoselacException;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public abstract class Obrada<T extends Entitet> {

    protected T entitet;
    protected Session session;

    public abstract List<T> read();

    protected abstract void kontrolaUnos() throws NovoselacException;

    protected abstract void kontrolaPromjena() throws NovoselacException;

    protected abstract void kontrolaBrisanje() throws NovoselacException;

    public Obrada() {
        this.session = HibernateUtil.getSession();
    }

    public void create() throws NovoselacException {
        if (entitet == null) {
            throw new NovoselacException("Entitet je null");
        }

        kontrolaUnos();
        persist();
    }

    public void update() throws NovoselacException {
        kontrolaPromjena();
        persist();
    }

    public void delete() throws NovoselacException {
        kontrolaBrisanje();
        session.beginTransaction();
        session.remove(entitet);
        session.getTransaction().commit();

    }

    private void persist() {
        session.beginTransaction();
        session.persist(entitet);
        session.getTransaction().commit();
    }

    public T getEntitet() {
        return entitet;
    }

    public void setEntitet(T entitet) {
        this.entitet = entitet;
    }

}
