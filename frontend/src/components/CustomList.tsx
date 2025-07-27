// ✅ src/components/CustomList.tsx

import { useCustomExtensions } from '@/hooks/useCustomExtensions'
import { Pill } from '@/components/common/Pill'

export const CustomList = () => {
  const { customList, loading, removeExtension } = useCustomExtensions()

  if (loading) return <p>로딩 중...</p>

  const list = Array.isArray(customList) ? customList : []

  return (
    <div className="pt-3 pb-4">
      <p className="text-xs text-gray-400 mb-1">
        {list.length} / 200
      </p>
      <div className="flex flex-wrap gap-2">
        {list.map((item) => (
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
