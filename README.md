# <u><center>📚Virtual Library Management System📚</center></u>

Welcome to the Virtual Library Management System! Our project is designed to modernize and streamline library operations, providing an efficient solution for managing resources, transactions, and insights. Leveraging the power of Java and flat file storage, our system offers a user-friendly interface and robust functionalities to enhance the library experience for both administrators and patrons.

---

## 💻 Tech Stack 🗃
### Core Language: Java 17 and above
### Data Storage: Use flat file storage (e.g., **.txt** or **.csv**) for storing book details and borrow logs.

---

## 💻 SetUp 🗃
- Step 1: First Install JDK
- Step 2: To Clone the Repository [https://github.com/HrishiNMore/myvirtuallibrarysystem.git]
- Step 3: To Run Project Locally
    - To Compile code - `javac VirtualLibrarySystem.java`
    - To Run code - `java VirtualLibrarySystem`

---

## 🎁 Features
### 📕 Book Management
- **➕ Add Books ➕**
    - Allow the addition of new books to the library's inventory.
    - Capture essential details: Title, Author, ISBN, Genre, Publication Date, and Number of Copies.
    - Ensure that the ISBN is Unique, acting as a Primary Identifier for each book.
    - Allow batch uploads where multiple books can be added at once, perhaps from a CSV or similar file format.

### 🔍 Search & View Books 🔍
- Provide a Search Functionality using various Criteria like Title, Author, or ISBN.
- Display Detailed views of Individual Books, showing all their attributes and current availability.

### 🖥 Transaction Management
- **📥 Borrow Books 📥**
    - Facilitate the Borrowing process using a book's ISBN.
    - Log Details of the Borrowing Transaction, including the userID and Date of Borrowing.
    - Update the inventory to reflect the decreased count of available copies.
    - Ensure an error prompt if a user tries to borrow a book that's currently unavailable.

- **📤 Return Books 📤**
    - Accommodate the return process using the book's ISBN and user ID.
    - Record the return transaction details, including the exact date of return.
    - Update the inventory to restore the count of the book's available copies.
    - Implement a system to manage and notify overdue returns.

### 🖥 Insights and Analytics
- **📊 Library Statistics 📊**
    - Display a dashboard or report summary of the library's vital statistics.
    - Offer a real-time count of the total books in the library.
    - Showcase the number of books currently borrowed and their respective titles.
    - Highlight the most popular books, ranked by their borrow count over a specified period.

- **📈 Trend Analysis 📉**
    - Feature insights on borrowing trends over time, indicating peak borrowing periods or seasonal preferences.
    - Illustrate the genres or authors that are currently in Demand and Aiding in future procurement decisions.
