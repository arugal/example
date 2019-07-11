local incr = redis.call('INCRBY', KEYS[1], 1)

local target = tonumber(ARGV[1])

if incr > target
then
    redis.call('INCRBY', KEYS[1], -1)
end