```toml
name = 'PUT Product '
method = 'PUT'
url = 'http://localhost:8181/api/v1/products/1'
sortWeight = 4000000
id = '527068c4-34a0-43ee-a154-1d32f5698086'

[[headers]]
key = 'Content-Type'
value = 'application/json'

[body]
type = 'JSON'
raw = '''
{
  "productName": "Iphone",
  "productPrice": 100.0,
  "productQuantity": 5
}
'''
```
