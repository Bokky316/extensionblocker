import { useCustomExtensions } from '@/hooks/useCustomExtensions'
import { Pill } from '@/components/common/Pill'
import type { CustomExtension } from '@/types'

export const CustomList = () => {
  const { customList, loading, removeExtension } = useCustomExtensions()
  const list = customList as CustomExtension[]

  if (loading) return <p>로딩 중...</p>

  return (
    <div className="pt-3 pb-4">
      <p
        style={{
          fontSize: '10px',
          color: '#9ca3af',
          marginBottom: '5px',
        }}
      >
        {customList.length} / 200
      </p>

      <div className="flex flex-wrap gap-2">
        {customList.map(
          (item) =>
            item && (
              <Pill
                key={item.id}
                label={item.name}
                onRemove={() => removeExtension(item.id)}
              />
            )
        )}
      </div>
    </div>
  )
}
