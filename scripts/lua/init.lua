box.cfg{}

s = box.schema.space.create('bank_account_info')

s:format({
    { name = 'uuid', type = 'string' },
    { name = 'first_name', type = 'string' },
    { name = 'last_name', type = 'string' },
    { name = 'city', type = 'string' },
    { name = 'blackListed', type = 'boolean' }
})

s:create_index('FIO_AND_CITY_INDEX', {
    type = 'hash',
    parts = { 'first_name','last_name','city' }
})