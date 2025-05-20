package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.Manager;
/** * Manager entities' Data Access Object (DAO) interface uses Manager as the type parameter to extend the generic DAO interface.

 * * For Manager entities in the system, this interface offers CRUD (Create, Read, Update, Delete) operations *. It is type-safe for Manager objects and inherits all fundamental data access methods by extending the generic DAO interface.</p>
 * * If the application requires it, manager-specific query methods can be added here.</p>
 */

public interface ManagerDao extends DAO<Manager> {
}
