export const CustomList = () => {
  const { customList, loading, removeExtension } = useCustomExtensions()

  const list = Array.isArray(customList) ? customList : []

  if (loading) return <p>로딩 중...</p>
  if (!Array.isArray(list)) return <p>확장자 데이터를 불러올 수 없습니다.</p>

  return (
    <div className="pt-3 pb-4">
      <p className="text-xs text-gray-400 mb-1">
        {list?.length ?? 0} / 200
      </p>
      <div className="flex flex-wrap gap-2">
        {(list ?? []).map((item) => (
          <Pill
            key={item.id}
            label={item.name}
            onRemove={() => removeExtension(item.id)}
          />
        ))}
      </div>
    </div>
  )
}
