/*
 * Copyright 2018 NOTEDESENVSP1.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.banco.controller.dao;

import com.banco.model.*;
import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author NOTEDESENVSP1
 */
public abstract class SessionGenerator {

    private static final Class cls1 = BancoBrasil.class,
            cls2 = Pessoa.class,
            cls3 = Transferencia.class,
            cls4 = Contato.class;
    static final String caminho = "hibernate.cfg.xml";

    static final Configuration config;
    static final Properties configProperties;
    static final StandardServiceRegistryBuilder serviceRegisteryBuilder;
    static final ServiceRegistry serviceRegistry;
    static SessionFactory factory;
    
    static {
        config = new Configuration()
            .addAnnotatedClass(cls1)
            .addAnnotatedClass(cls2)
            .addAnnotatedClass(cls3)
            .addAnnotatedClass(cls4)
            .configure(caminho);
        configProperties = config.getProperties();
        serviceRegisteryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistry = serviceRegisteryBuilder.applySettings(configProperties).build();
        factory = config.buildSessionFactory(serviceRegistry);
    }
   
    public Session getSession() {
        return factory.getCurrentSession();
    }

    public void closeSession() {
        factory.close();
    }

    public void criarTabela() {
        new SchemaExport(config).create(true, true);
        factory.close();
    }

}
