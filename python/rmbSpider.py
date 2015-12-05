import scrapy
from scrapy.item import Item, Field

class Currency(Item):
   date = Field()
   usd = Field()
   euro = Field()

items = []

class BlogSpider(scrapy.Spider):
    name = 'rmbspider'
    start_urls = ['http://www.safe.gov.cn/AppStructured/view/project!RMBQuery.action']

    def parse(self, response):
        rows = response.selector.xpath('//table[@id="InfoTable"]//tr')
        for row in rows:
          item = Currency()
          date = row.xpath('td[1]/text()').stripe()
          item['date'] = date
          items.append(item)
        return items
