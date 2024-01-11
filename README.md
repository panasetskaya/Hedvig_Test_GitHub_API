notes:
- I added pagination for the user search, but limited it to 3 pages in order not to exceed the GitHub API limitations for not auth-ed users (60 requests per hour)
- For the same reason, I left user repositories (and repositories details) GET requests without pagination, getting just a simple one-page result
- also because of these limitations the searchBar does not perform search on query change, it's performed only when the search query is submitted (by clicking on "Go" or by Enter key)
