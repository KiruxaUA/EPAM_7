package ua.epam6.IOCRUD.service;

import ua.epam6.IOCRUD.service.servicevisitors.ServiceVisitor;

public interface Serviceable {
    void doService(ServiceVisitor visitor);
}
