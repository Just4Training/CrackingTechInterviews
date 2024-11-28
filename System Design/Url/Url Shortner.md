# Url Shortner
![Url Shortner Architecture](https://github.com/Just4Training/CrackingTechInterviews/blob/main/System%20Design/Url/Url%20Shortner.png?raw=true)

## Key points

### Base62 vs Base64
- **Base62:**
    - Uses 62 characters: 0-9, A-Z, and a-z.
    - It is case-sensitive but does not include special characters like + or /, which makes it URL- and filename-friendly.
    - Base62 is commonly used for URL shortening, short unique IDs, or places where you need a compact, alphanumeric representation without special characters.
    - Ideal for applications that require compact, alphanumeric-only text representations without special characters, such as:
        - URL shortening services.
        - Generating human-friendly unique IDs.
        - Storing data in filenames without needing encoding.

- Base64:
    - Uses 64 characters: A-Z, a-z, 0-9, +, and /.
    - It also includes = as a padding character at the end if needed.
    - Base64 is widely used for encoding binary data in text form, such as in email attachments, data URLs, and encoding binary content in JSON or XML. Because it includes + and /, it can require additional encoding for URL compatibility.
    - Commonly used for encoding binary data in text-based protocols like:
        - Email attachments (MIME encoding).
        - Embedding binary data in JSON, XML, or HTML (e.g., image data URIs).
        - Storing binary data in text-based formats, such as database strings.
