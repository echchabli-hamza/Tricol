# Tricol - Supplier Orders Management API

![Spring Boot](# Tricol - Supplier Orders Management API

![Planification](https://echchablihamza1-1758101303053.atlassian.net/jira/software/projects/TRIC/boards/35/calendar?date=2025-10-01)
![Diagram de class](https://lucid.app/lucidchart/e93244c1-696c-463f-ad84-a206b2813ebd/edit?invitationId=inv_60abb0e4-c01e-4818-b7d2-91f75e704ec6&page=0_0#)

## Overview

**Tricol** is a company specialized in professional workwear. This project is a **Spring Boot REST API** designed to manage supplier orders and stock movements, enabling Tricol to digitize and optimize its procurement and inventory processes.

The API covers the full lifecycle of supplier orders, from creation to delivery, including automatic stock updates and cost valuation (FIFO / CUMP).

---

## Features

### Supplier Management
- Add, update, delete, and view suppliers.
- Supplier details: company name, address, contact, email, phone, city, ICE.

### Product Management
- Manage products associated with orders.
- Product details: name, description, unit price, category.

### Supplier Orders Management
- Create, update, or cancel supplier orders.
- Associate orders with a supplier and a list of products.
- Automatic calculation of the total order amount.
- Order status: `EN_ATTENTE`, `VALIDÉE`, `LIVRÉE`, `ANNULÉE`.

### Stock Movements & Cost Valuation
- Automatic creation of stock movements when an order is delivered.
- Stock types: `ENTREE`, `SORTIE`, `AJUSTEMENT`.
- Automatic stock quantity updates.
- Cost calculation:
  - **FIFO** (First In, First Out)
  - **CUMP** (Weighted Average Cost)
- Configurable cost method (default: CUMP).
- Query stock history by product, movement type, or associated order.

### Pagination & Filtering
- All main entities (suppliers, products, orders) support pagination and filtering.
- API supports:
  - `page` (default: 0)
  - `size` (default: 10)
  - `sort` (e.g., `sort=societe,asc`)

---

## Technology Stack

- **Spring Boot**
- **Spring Data JPA**
- **MapStruct** for DTO mapping
- **Liquibase** for database migrations
- **Swagger / OpenAPI** for automatic API documentation
- **Jakarta Validation** for field validation
- PostgreSQL (or your preferred RDBMS)

---

## Architecture

The project follows a layered architecture:

)
![Java](https://img.shields.io/badge/Java-21-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## Overview

**Tricol** is a company specialized in professional workwear. This project is a **Spring Boot REST API** designed to manage supplier orders and stock movements, enabling Tricol to digitize and optimize its procurement and inventory processes.

The API covers the full lifecycle of supplier orders, from creation to delivery, including automatic stock updates and cost valuation (FIFO / CUMP).

---

## Features

### Supplier Management
- Add, update, delete, and view suppliers.
- Supplier details: company name, address, contact, email, phone, city, ICE.

### Product Management
- Manage products associated with orders.
- Product details: name, description, unit price, category.

### Supplier Orders Management
- Create, update, or cancel supplier orders.
- Associate orders with a supplier and a list of products.
- Automatic calculation of the total order amount.
- Order status: `EN_ATTENTE`, `VALIDÉE`, `LIVRÉE`, `ANNULÉE`.

### Stock Movements & Cost Valuation
- Automatic creation of stock movements when an order is delivered.
- Stock types: `ENTREE`, `SORTIE`, `AJUSTEMENT`.
- Automatic stock quantity updates.
- Cost calculation:
  - **FIFO** (First In, First Out)
  - **CUMP** (Weighted Average Cost)
- Configurable cost method (default: CUMP).
- Query stock history by product, movement type, or associated order.

### Pagination & Filtering
- All main entities (suppliers, products, orders) support pagination and filtering.
- API supports:
  - `page` (default: 0)
  - `size` (default: 10)
  - `sort` (e.g., `sort=societe,asc`)

---

## Technology Stack

- **Spring Boot**
- **Spring Data JPA**
- **MapStruct** for DTO mapping
- **Liquibase** for database migrations
- **Swagger / OpenAPI** for automatic API documentation
- **Jakarta Validation** for field validation
- PostgreSQL (or your preferred RDBMS)

---

## Architecture

The project follows a layered architecture:

