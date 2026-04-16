🤝 GivingHand
Humanitarian Logistics & Resource Distribution Platform
GivingHand is a modular enterprise application built with Jakarta EE to streamline charitable operations. It bridges the gap between donors and organizations, providing real-time tracking of humanitarian needs, automated inventory management, and a secure donation lifecycle.

🏗 System Architecture
The project follows a Strict Clean Architecture (N-Tier) to ensure separation of concerns and scalability:

api (JAX-RS): The presentation layer. Handles HTTP requests, manages REST resources, and returns DTOs.

service (EJB 3.2): The core business logic layer using Stateless Session Beans. Handles transaction management and application rules.

repo (JPA): Data Access layer using EntityManager for database interactions.

dto (Data Transfer Objects): Protects the domain models by transferring data in a decoupled way.

entity (JPA): Represents the persistence schema (User, Campaign, Warehouse, etc.).

mapper: Logic for converting between Entities and DTOs.

utilities: Shared logic, custom exceptions (e.g., BusinessException), and enums.

🛠 Tech Stack & Enterprise Standards
Framework: Jakarta EE 10 (Targeting modern enterprise environments).

Business Logic: EJB 3.x (Stateless Beans).

Persistence: JPA 3.x with Hibernate as the provider.

Messaging: JMS (Jakarta Messaging) via ActiveMQ Artemis for asynchronous notifications.

Transactions: JTA (Java Transaction API) for ACID compliance (crucial for resource allocation).

Security: JAAS / Jakarta Security for Role-Based Access Control (RBAC).

Server: WildFly 27+ (Full Profile).

Documentation: Postman API Collection included.

🚀 Key Features (Phase 1)

✅ User Management: Profile updates and identity management.

⏳ Secure Auth: JWT/Session-based authentication with role differentiation (Donor vs. Organization).

⏳ Campaign Engine: itemized humanitarian needs with real-time status tracking.

⏳ Inventory & JTA: Virtual warehouse management with atomicity in resource transfer.

⏳ Smart Alerts: Asynchronous "Low Stock" and "Donation Received" notifications via MDBs.

⚙️ Development Setup
Prerequisites
JDK 17+

IntelliJ IDEA Ultimate

WildFly 27.0.1.Final (Full Profile)

Deployment
Clone: git clone https://github.com/YourUsername/GivingHand.git

Datasource: Configure GivingHandDS in WildFly standalone-full.xml.

JMS Queue: Create NotificationQueue in the messaging subsystem.

Run: Deploy the exploded WAR/EAR via IntelliJ.