local incr = redis.call('INCRBY', KEYS[1], 1)

if incr > ARGV[1]
then
    redis.call('INCRBY', KEYS[1], -1)
    return ">"
end

if incr == ARGV[1]
then return "="
end

return "<"