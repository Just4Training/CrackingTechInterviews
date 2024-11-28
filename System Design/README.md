# System Design
This section focus on high-level **System Design**, including **Key Technologies, Concepts** and **System Design Instance**

## System Design Template
1. Requirement analysis (Functional and Non-functional)
2. List Core Entities
3. API design (try to list API methods and API payload)
4. High level Structure (Focus on solve **Functional Requirement**)
5. Dive deep (Focus on solve **Non-functional Requirement**)

### Requirement analysis (~5min)
- Functional Requirement: What the Product/System should do?
- Non-Functional Requirement: CAP theorem (Accessibility? Consistency>)? Estimate the DAU, QoS, Latency

### API Design

## System Design Concept
### Scaling
**Work Distribution:** The first challenge of horizontal scaling is getting the work to the right machine. This   is often done via a load balancer
- Round-robin
- Queueing System

**Data Distribution:** Sharding

Amazon Elastic Container Service (ECS) is a fully managed container orchestration service that helps you to more efficiently deploy, manage, and scale containerized applications. [AWS ECS](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/service-auto-scaling.html)


### Locking: 
Locking is the process of ensuring that only one client can access a shared resource at a time.
- Pessimistic Locking: database is directly utilized to lock a specific ticket row, ensuring exclusive access to the first user trying to book it. Done by *SELECT FOR UPDATE* SQL.
- Status & Expiration Time with Cron: adding a status field and expiration time on the ticket table.
- Distributed Lock with TTL: use a key-value store to store a lock and then use the atomicity of the key-value store to ensure that only one process can acquire the lock at a time.

#### Distributed Locks
1. **Locking Mechanisms:** There are different ways to implement distributed locks. One common implementation uses Redis and is called **Redlock**. Redlock uses multiple Redis instances to ensure that a lock is acquired and released in a safe and consistent manner.
2. **Lock Expiry:** Distributed locks can be set to expire after a certain amount of time. This is important for ensuring that locks don't get stuck in a locked state if a process crashes or is killed.
3. **Locking Granularity:** Distributed locks can be used to lock a single resource or a group of resources. For example, you might want to lock a single ticket in a ticketing system or you might want to lock a group of tickets in a section of a stadium.
4. **Deadlocks:** Deadlocks can occur when two or more processes are waiting for each other to release a lock.

### Communication Protocols
- **REST:** Request -> Response
- **SSE:** Server-sent Events
- **WebSocket:** Bi-directional Channel, are necessary if you need realtime, bidirectional communication between the client and the server
![3 types protocols](https://d248djf5mc6iku.cloudfront.net/excalidraw/2bb627034b62ecdf1c88edf37e3028dd)

### Queue
- Using Queue when there is a heavy traffic, usually work with workers(compute resource)
- **Be careful of introducing queues into synchronous workloads. If you have strong latency requirements (e.g. < 500ms), introducing a queue will likely break your latency constraints**
#### Things to know about Queue
1. **Message Ordering:** Most queues are FIFO (first in, first out), meaning that messages are processed in the order they were received. However, some queues (like Kafka) allow for more complex ordering guarantees, such as ordering based on a specified priority or time.
2. **Retry Mechanisms:** Many queues have built-in retry mechanisms that attempt to re-deliver a message a certain number of times before considering it a failure. You can configure retries, including the delay between attempts, and the maximum number of attempts.
3. **Dead Letter Queues:** Dead letter queues are used to store messages that cannot be processed. They're useful for **debugging** and **auditing**, as it allows you to inspect messages that failed to be processed and understand why they failed.
4. **Scaling with Partitions:** Queues can be partitioned across multiple servers so that they can scale to handle more messages. Each partition can be processed by a different set of workers. Just like databases, you will need to specify **a partition key** to ensure that related messages are stored in the same partition.
5. **Backpressure:** Backpressure is a way of slowing down the production of messages when the queue is overwhelmed. This helps prevent the queue from becoming a bottleneck in your system. For example, if a queue is full, you might want to reject new messages or slow down the rate at which new messages are accepted, potentially returning an error to the user or producer.

#### AWS SQS
[Amazon Simple Queue Service](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/welcome.html)

### Database
- 
- full text indexes
#### Database Types & Usage
| Type                        | Usage                                                | Examples                                           |
|-----------------------------|------------------------------------------------------|----------------------------------------------------|
| Relational Database (RDBMS) | Customer, Product, Financial transaction data, etc   | MySQL, PostgreSQL, Oracle Database                 |
| Key-Value Store             | Session Storage, Caching, real-time data processing  | Redis, DynamoDB                                    |
| Document DataBase           | Document-oriented info, CMS, reviews, json, etc      | MongoDB, Couchbase, Apache CouchDB                 |
| Graph Databse               | social Network, recommendation systems               | Neo4j, Amazon Neptune                              |
| In-Memory Database          | RAM, Online Gaming, High-Frequency Trading           | Redis, Memcached                                   |
| Time-Serise Database (TSDB) | Time-stamped or time-series data, Monitor, IoT       | InfluxDB, TimescaleDB, Prometheus                  |
| Wide-Column Stores          | Web analytics and user tracking, Real-Time Analytics | Apache Cassandra, Apache HBase, Google Bigtable    |
| Object-Oriented Database    | OOP application, multi-media                         | ObjectDB, db4o                                     |
| Text Search Database        | Seach Engine, Log analysis                           | Elastic Search, Apache Solr, Sphinx                |
| Spatial Database            | geographical or spatial information.                 | PostGIS (extension for PostgreSQL), Oracle Spatial |
| Vector Database             | Image and Video Search                               | Faiss, Milvus, Pinecone                            |
| Blob Datastore              | Files, images, audio and videos, CDN                 | Amazon S3, Azure Blob Storage, HDFS                |
| Ledger Database             | Blockchain, immutable, append-obly record, voting    | Amazon Quantum Ledger Database (QLDB)              |
| Hierarchical Database       | Tree-like structure, file system                     | IBM IMS, Windows Registry                          |
| Embedded Database           | Gaming, Desktop Applications                         | SQLite, RocksDB, Berkeley DB                       | 

Ref: [15 types of Database](https://blog.algomaster.io/p/15-types-of-databases)

### Pagination
> RDBMS: LIMIT 100 OFFSET 0 // first page OFFSET 100 second page
> MongoDB: Model.find().limit(100).skip((page - 1) * limit)

### SQL vs NoSQL
RDBMS Transactions: Transactions are a way of grouping multiple operations together into a single atomic operation. For example, if you have a users table and a posts table, you might want to create a new user and a new post for that user at the same time. If you do this in a transaction, either both operations will succeed or both will fail. This is important for maintaining data integrity.
- ACID: Atomicity, Consistency, Isolation and Durability
	![NoSQL](https://d248djf5mc6iku.cloudfront.net/excalidraw/641b9db6ecff33edf227cec61e2f6d86)

### Optimistic concurrency control (OCC)
- OCC assumes that multiple transactions can frequently complete without interfering with each other. While running, transactions use data resources without acquiring locks on those resources. Before committing, each transaction verifies that no other transaction has modified the data it has read. If the check reveals conflicting modifications, the committing transaction rolls back and can be restarted. Mainly used for low data race. 

#### Invert index


### What is full-text search?
Unlike traditional search methods that rely on exact word or phrase matches, a full-text search refers to a search of all of the documents' contents within the full-text queries’ range(s) that are relevant. This includes topic, phrasing, citation, or additional text attributes.

## Content
|Content               |Link                                  |
|----------------------|--------------------------------------|
|Url Shortner          |tbd|

> Good Reference for system design:
> - [Hello Interview](https://www.hellointerview.com/learn/system-design/in-a-hurry/introduction)