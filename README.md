# TechnicoRenovations api
### Description:
A  Renovation  Contractor  Agency,  Technico,  within  the  framework  of  its  operation,  needs  an application that will enable the employees - managers of its platform to have access to information concerning customers and repairs. It will also enable its customers to oversee the progress of repair / renovation work on their property. All the data are stored in an appropriate database.  
Functionalities:
* Property Owner’s Page:
    * Create Owner
    * Search Owner by VAT Number & Email
    * Update Owner Information
    * Delete Owner
* Property Page:
    * Create Property
    * Search Property by VAT Number & Identification Number
    * Update Property Information
    * Delete Property with no repairs
* Repair Order Page:
    * Create Repair Order
    * Search Order by Date, Range of Dates & User ID
    * Update Order Information
    * Delete Order

---
### Set of endpoints created:
* Owner’s Endpoints
    * Creating Owner
    * Searching Owner with ID
    * Searching Owner with VAT Number
    * Searching Owner with Email
    * Updating Owner fields
    * Updating Owner fields & Property
    * Deleting Owner (safely and permanently)

* Property Endpoints
    * Creating Property
    * Searching Property with ID
    * Searching Property with VAT Number of Owner
    * Searching Property with Identification Number of Property
    * Updating Property fields
    * Updating Property fields & Owner
    * Deleting Property (safely and permamently)

* Repair Order Endpoints
    * Creating Repair Order
    * Searching Repair Order with ID
    * Searching Repair Order with Owner’s ID
    * Searching Repair Order with Date
    * Searching Repair Order with range of Dates
    * Updating Repair Order fields
    * Updating Repair Order fields & Property
    * Deleting Repair Order (safely and permamently)

---
### Technologies used:
* Java SE 17
* Jakarta EE
* MySQL Server
* Application Server WildFly 27.0.1
* Maven
* jUnit
* MySQL and Workbence
* Github
* Jpa Hibernate

---
### Requirements
Technico project requires the following to run:
* JDK 17 or newer.
* Database: Use of MySql Server
* IDE: NetBeans 15

---
### Special Thanks...
#### Special Thanks to our Instructors from Codehub:
* Odysseas Efremidis
* Thomas Varsamidis
* Dimitris Iracleous
* Ioannis Daniil
* Ioannis Klian
* Constantinos Giannacoulis
