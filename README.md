# Web Search Engine

# Demo
Video - https://youtu.be/pmLHPfoaJHU

# Introduction
We have implemented Web Search Engine in java which takes 10000+ websites as input, index it and returns top 10 pages according to query. We have implemented functionalities like Crawler, Indexing, Parsing, Spell Checking and Suggestions for Query. We have used concepts like Inversed Index for indexing, TST(Ternary Search Trie) to store indexed documents, Term Frequency - Inversed Document Frequency to measure how important a word for a document compared to other documents, HashSet in crawler to check whether url is crawled or not in constant time, HashMap to keep document id and document information record, Sorting(Collections.sort - mergesort) to sort ranked pages, regex to split document in words, Jsoup, Edit distance for spell checking.

# How to run
- Import project in eclipse
- Include external library Jsoup in project
- Run
